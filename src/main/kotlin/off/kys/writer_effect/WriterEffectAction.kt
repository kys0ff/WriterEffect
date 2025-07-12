package off.kys.writer_effect

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.editor.ScrollType
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidator
import com.intellij.openapi.ui.Messages
import kotlinx.coroutines.*
import kotlinx.coroutines.swing.Swing
import org.intellij.markdown.ExperimentalApi
import org.jetbrains.annotations.NotNull
import kotlin.coroutines.CoroutineContext

@ExperimentalApi
class WriterEffectAction : AnAction(), CoroutineScope {
    private val job = SupervisorJob()
    
    override val coroutineContext: CoroutineContext = Dispatchers.Swing + job

    override fun actionPerformed(e: AnActionEvent) {
        val project: Project = e.project ?: return
        val editor = FileEditorManager.getInstance(project).selectedTextEditor
        if (editor == null) {
            Messages.showInfoMessage("No editor is currently open.", "Writer Effect")
            return
        }

        val document = editor.document
        val originalText = document.text

        // Ask for delay between characters
        val delayInput = Messages.showInputDialog(
            project,
            "Enter delay in milliseconds between characters:",
            "Customize Writer Effect",
            Messages.getQuestionIcon(),
            "50",
            object : InputValidator {
                override fun checkInput(inputString: @NotNull String?): Boolean = inputString?.toIntOrNull() != null

                override fun canClose(inputString: @NotNull String?): Boolean = inputString?.toIntOrNull() != null
            }
        ) ?: return

        val delayMillis = delayInput.toLongOrNull()?.coerceAtLeast(1) ?: 50L

        // Ask for an initial wait before start
        val waitInput = Messages.showInputDialog(
            project,
            "Enter wait before start (in seconds):",
            "Customize Writer Effect",
            Messages.getQuestionIcon(),
            "0",
            object : InputValidator {
                override fun checkInput(inputString: @NotNull String?): Boolean = inputString?.toIntOrNull() != null

                override fun canClose(inputString: @NotNull String?): Boolean = inputString?.toIntOrNull() != null
            }
        ) ?: return

        val waitSeconds = waitInput.toLongOrNull()?.coerceAtLeast(0) ?: 0L

        val effectJob = launch {
            if (waitSeconds > 0) delay(waitSeconds * 1000)

            runWriteAction { document.setText("") }

            for (i in originalText.indices) {
                if (!isActive) break
                delay(delayMillis)
                val partialText = originalText.substring(0, i + 1)

                safeWriteOnEdt {
                    document.setText(partialText)
                    editor.caretModel.moveToOffset(partialText.length)
                    editor.scrollingModel.scrollToCaret(ScrollType.MAKE_VISIBLE)
                }
            }
        }

        WriterEffectController.activeJob = effectJob
        WriterEffectController.originalText = originalText
        WriterEffectController.project = project
    }

    private fun safeWriteOnEdt(block: () -> Unit) {
        runCatching {
            if (ApplicationManager.getApplication().isDispatchThread) {
                runWriteAction(block)
            } else {
                ApplicationManager.getApplication().invokeAndWait {
                    runWriteAction(block)
                }
            }
        }
    }
}
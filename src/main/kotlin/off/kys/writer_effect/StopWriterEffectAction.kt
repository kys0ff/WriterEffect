package off.kys.writer_effect

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.editor.ScrollType
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.ui.Messages

class StopWriterEffectAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = WriterEffectController.project
        val originalText = WriterEffectController.originalText
        val job = WriterEffectController.activeJob

        if (project == null || originalText == null || job == null) {
            Messages.showInfoMessage("No writer effect is currently running.", "Writer Effect")
            return
        }

        // Cancel the coroutine job
        job.cancel()
        WriterEffectController.activeJob = null

        val editor = FileEditorManager.getInstance(project).selectedTextEditor
        if (editor == null) {
            Messages.showErrorDialog("No active editor found to restore text.", "Writer Effect")
            return
        }

        val document = editor.document

        runWriteAction {
            document.setText(originalText)
            editor.caretModel.moveToOffset(document.textLength)
            editor.scrollingModel.scrollToCaret(ScrollType.MAKE_VISIBLE)
        }

        WriterEffectController.reset()
    }
}
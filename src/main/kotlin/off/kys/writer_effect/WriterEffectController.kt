package off.kys.writer_effect

import com.intellij.openapi.project.Project
import kotlinx.coroutines.Job

object WriterEffectController {
    var activeJob: Job? = null
    var originalText: String? = null
    var project: Project? = null

    fun reset() {
        activeJob = null
        originalText = null
        project = null
    }
}
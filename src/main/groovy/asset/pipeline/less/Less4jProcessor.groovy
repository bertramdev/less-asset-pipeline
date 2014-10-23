package asset.pipeline.less

import asset.pipeline.AssetHelper
import com.github.sommeri.less4j.LessCompiler
import com.github.sommeri.less4j.core.ThreadUnsafeLessCompiler
import com.github.sommeri.less4j_javascript.Less4jJavascript
import groovy.util.logging.Log4j

@Log4j
class Less4jProcessor {

    def precompilerMode

    Less4jProcessor(precompiler = false) {
        this.precompilerMode = precompiler ? true : false
    }

    def process(input, assetFile) {
        try {

            def lessSource = new AssetPipelineLessSource(assetFile, input, [baseFile: assetFile])

            LessCompiler.Configuration configuration = new LessCompiler.Configuration()
            Less4jJavascript.configure(configuration);
            LessCompiler compiler = new ThreadUnsafeLessCompiler();
            def compilationResult = compiler.compile(lessSource, configuration);

            def result = compilationResult.getCss()

            return result
        } catch (Exception e) {
            if (precompilerMode) {
                def errorDetails = "LESS Engine Compiler Failed - ${assetFile.name}.\n"
                errorDetails += "**Did you mean to compile this file individually (check docs on exclusion)?**\n"
                log.error(errorDetails, e)
            } else {
                throw e
            }
        }
    }

}

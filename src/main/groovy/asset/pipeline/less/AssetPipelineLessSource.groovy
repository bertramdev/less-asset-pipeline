package asset.pipeline.less

import com.github.sommeri.less4j.*
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import asset.pipeline.CacheManager
import asset.pipeline.AssetHelper
import asset.pipeline.AssetPipelineConfigHolder
import asset.pipeline.AssetFile
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


class AssetPipelineLessSource extends LessSource {
	def sourceFile
	String contents
	Map options
	public AssetPipelineLessSource(file, contents, options=[:]) {
		sourceFile = file
		this.options = options
		this.contents = contents

	}

 	public LessSource relativeSource(String fileName) {
			def newFile
 		    if( fileName.startsWith( AssetHelper.DIRECTIVE_FILE_SEPARATOR ) ) {
                newFile = AssetHelper.fileForUri( fileName, 'text/css', null, options.baseFile )
            }
            else {
                def relativeFileName = [ sourceFile.parentPath, fileName ].join( AssetHelper.DIRECTIVE_FILE_SEPARATOR )
                newFile = AssetHelper.fileForUri( relativeFileName, 'text/css', null, options.baseFile )
            }


            if( !newFile && !fileName.startsWith( AssetHelper.DIRECTIVE_FILE_SEPARATOR ) ) {
				newFile = AssetHelper.fileForUri( AssetHelper.DIRECTIVE_FILE_SEPARATOR + fileName, 'text/css', null, options.baseFile )
            }
            else if (!newFile) {
                log.warn( "Unable to Locate Asset: ${ fileName }" )
            }

			if(newFile) {
				return new AssetPipelineLessSource(newFile, null, options)
			}

 		   //  if(matchedPath) {
 		   //  	def matchedFile = new File(matchedPath,filename)
 		   //  	if(options.baseFile) {
			// 		CacheManager.addCacheDependency(options.baseFile.file.canonicalPath, matchedFile)
			//
 		   //  	}
 		   //  	return new AssetPipelineLessSource(matchedFile,null,options)
 		   //  }

 		   //  def matchingPath =
		    // log.debug "resolveUri: path=${path}"
		    // for (Object index : paths.getIds()) {
		    //   def it = paths.get(index, null)
		    //   def file = new File(it, path)
		    //   log.trace "test exists: ${file}"
		    //   if (file.exists()) {
		    //     log.trace "found file: ${file}"
		    //     if(assetFile) {
		    //       CacheManager.addCacheDependency(assetFile.file.canonicalPath, file)
		    //     }
		    //     return file.toURI().toString()
		    //   }
		    // }

		    return null
 	}


	public String getContent() {
		if(contents) {
			return contents
		}
		return sourceFile.inputStream.text
	}

	public byte[] getBytes() {
		if(contents) {
			return contents.bytes
		}
		return sourceFile.inputStream.bytes
	}
}

package asset.pipeline.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project


class LessAssetPipelinePlugin implements Plugin<Project> {
	void apply(Project project) {
		asset.pipeline.AssetHelper.assetSpecs << asset.pipeline.less.LessAssetFile
	}
}

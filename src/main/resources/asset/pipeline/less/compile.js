var compile = function(fileText, paths) {
    var me = this;
    globalPaths = paths;

    var parser = new(less.Parser);

    var result;
    less.render(fileText,{}, function(e, output) {
        var lessResults = {
            success: true,
            css: output.css
        };
        Packages.asset.pipeline.less.LessProcessor.setResults(lessResults)
    })
    return result;
};

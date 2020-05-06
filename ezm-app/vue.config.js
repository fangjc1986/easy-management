const path = require('path');
const fs = require('fs');
const webpack = require('webpack');

function resolve(dir) {
    return path.join(__dirname, dir)
}

module.exports = {
    outputDir: './ezm-pro-app-dist',
    publicPath: './',
    lintOnSave: false,
    productionSourceMap: false,
    chainWebpack: (config) => {
        config.resolve.alias
            .set('@', resolve('src'));
        config.entry.app = ["babel-polyfill", "./src/main.js"];
    },
    configureWebpack: {
        plugins: [
            new webpack.DefinePlugin({
                'process.env.INDEX_MD': JSON.stringify({
                    content: fs.readFileSync("./README.md",'utf-8')
                }),
            }) ,
        ]
    },
    devServer: {
        // 端口配置
        port: 2005,
        // 反向代理配置
        proxy: {
            '/api': {
                target: 'http://localhost:8085',
                ws: true,
                pathRewrite: {
                    '^/api/ezm-server': '',
                }
            }
        }
    },
    transpileDependencies: [
        /[/\\]node_modules[/\\]pinyin[/\\]/,
    ]
};

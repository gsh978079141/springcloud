const {createProxyMiddleware} = require("http-proxy-middleware");

module.exports = function (app) {
    app.use(
        // 将原来的proxy改为createProxyMiddleware
        createProxyMiddleware("/apis", {
            pathRewrite: {
                "^/apis": ""
            },
            // target: "http://192.168.27.17:8001",
            target: "http://127.0.0.1:7006",
            changeOrigin: true,
        })
    );
};
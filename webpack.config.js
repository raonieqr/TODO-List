const path = require('path')

module.exports = {
    mode: 'production',
    entry: {
        script: './Frontend/script.js',
    },
    output: {
        filename: '[name].min.js',
        path: path.join(__dirname, 'dist')
    },
    module: {
        rules: [
          {
            test: /\.js$/,
            use: 'babel-loader',
            exclude: /node_modules/,
          },
        ],
      },
}
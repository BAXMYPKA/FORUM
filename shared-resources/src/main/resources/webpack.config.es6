const {CleanWebpackPlugin} = require('clean-webpack-plugin');
const path = require('path');


module.exports = {
	entry: './src/js/index-forum-src.es6',
	mode: "development",
	output: {
		path: path.resolve(__dirname, 'dist/js'),
		filename: 'index-forum.es6'
	},
/*
	plugins: [
		new CleanWebpackPlugin(),
	],
*/
	// watch: true,
	watchOptions:{
		poll: 2000,
		aggregateTimeout: 2000,
		ignored: /node_modules/
	},
	module: {
		rules: [
			{
				test: /\.(js|es6|jsx)$/,
				exclude: /(node_modules|bower_components)/,
				use: {
					loader: 'babel-loader',
					options: {
						presets: ['@babel/preset-env', '@babel/preset-react']
					}
				}
			},
		]
	},
	//Also MUST be installed globally by 'npm install -g webpack-dev-server webpack-cli'
	devServer: {
		contentBase: path.join(__dirname, "dist"),
		publicPath: "/js",
		compress: true,
		port: 9000,
		watchContentBase: true,
		progress: true,
		index: "index_forum.html"
	},
};
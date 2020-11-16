const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const path = require('path');

module.exports = {
	entry: './src/js/index-forum.es6',
	output: {
		path: path.resolve(__dirname, 'dist/js'),
		filename: 'index-forum.es6'
	},
	plugins: [
		new CleanWebpackPlugin(),
	]
};
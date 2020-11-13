const path = require('path');

module.exports = {
	entry: './src/forum-index.js',
	output: {
		path: path.resolve(__dirname, 'dist/js'),
		filename: 'forum-index.js'
	}
};
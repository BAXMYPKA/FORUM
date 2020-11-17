import React from 'react'
import {render} from 'react-dom'
import H1 from "./h1.jsx";

// let reactElement = React.createElement("input", "pro", null);
// let r = React.createElement("h3", "prol", null);

// const element = <h3>element</h3>;


const h1Element = <H1 text="FIRST H1"/>;

render(
	h1Element,
	document.getElementById('root')
);

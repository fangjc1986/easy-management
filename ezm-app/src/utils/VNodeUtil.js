export default {

    renderToHtml(vNode) {
        if (!vNode.tag) return document.createTextNode(vNode.text);
        if (!vNode.tag) return document.createTextNode(vNode.text);
        vNode.children.forEach(x => {
            el.appendChild(this.renderToHtml(x));
        });
        return el;
    },
    renderToHtmlText(vNode , deep = 0 ) {
        if (!vNode.tag) return vNode.text.trim().replace(/\n\s*$/, '') + '' ;
        // if (vNode.tag === 'br') return '\n' ;
        let s = '' ; //'    '.repeat(deep);
        let el = s + `<${vNode.tag}>`;
        if( vNode.children ) {
            vNode.children.forEach(x => {
                el += s + this.renderToHtmlText(x , deep + 1) ;
            });
        }
        el += s + `</${vNode.tag}>`;
        console.log( el );
        return el;
    },
    renderArrayToHtml(vNodeList) {
        let el = document.createElement('template');
        vNodeList.forEach(x => {
            el.appendChild(this.renderToHtml(x));
        });
        return el ;
    },
    renderArrayToHtmlText(vNodeList) {
        let el = '';
        vNodeList = vNodeList || [];
        vNodeList.forEach(x => {
            el += this.renderToHtmlText(x);
        });
        return el ;
    }

}
import Vue from "vue";
import VXETable from "vxe-table";

const setInitial = (str, capital = true) => {
    if (capital) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    } else {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

};

const toCamel = (str) => {
    return str.replace(/([^_])(?:_+([^_]))/g, function ($0, $1, $2) {
        return $1 + $2.toUpperCase();
    });
};

const componentNameToCamel = (Component) => {
    let c = toCamel(Component.name.replace(/\-/g, '_'));
    return setInitial(c, true);
};


const createProps = (renderOpts, params) => {
    let {row, column} = params;
    let {events} = renderOpts;
    return {
        value: renderOpts.inputFormatter ? renderOpts.inputFormatter(row[column.property], params) : row[column.property],
        options: renderOpts.options || [],
        ...(renderOpts.props || {})
    };
};

const createAttrs = (renderOpts, params) => {
    let {row, column} = params;
    let {events} = renderOpts;
    return {
        ...(renderOpts.attrs || {})
    };
};

const createOnInput = (renderOpts, params) => {
    let {row, column} = params;
    let {events} = renderOpts;
    return {
        input: (v) => {
            row[column.property] = renderOpts.outputFormatter ? renderOpts.outputFormatter(v, params) : v;
        }
    }
};


const vxeAddRender = (name, Component) => {
    VXETable.renderer.add(name, {
        renderDefault(h, renderOpts, params) {
            return [h(Component.name, {
                props: createProps(renderOpts, params),
                attrs: createAttrs(renderOpts, params),
                on: {
                    ...(renderOpts.events || {})
                }
            })];
        },
        renderFilter(h, renderOpts, params) {
            let {row, column} = params;
            let {events} = renderOpts;
            return [h(Component.name, {
                props: {
                    value: column.filters[0].data,
                    options: renderOpts.options || [],
                    ...(renderOpts.props || {})
                },
                attrs: createAttrs(renderOpts, params),
                on: {
                    input: (v) => {
                        column.filters[0].data = v;
                        params.$panel.changeAllOption(null, true, column.filters[0].data);
                    },
                    ...(renderOpts.events || {})
                }
            })];
        },
        renderEdit(h, renderOpts, params) {
            let {row, column} = params;
            let {events} = renderOpts;
            return [h(Component.name, {
                props: createProps(renderOpts, params),
                attrs: createAttrs(renderOpts, params),
                on: {
                    ...createOnInput(renderOpts, params),
                    ...(renderOpts.events || {})
                }
            })];
        },
    });
};

export const vxeTableAddRender = (Component) => {
    let componentName = componentNameToCamel(Component);
    Vue.component(componentName, Component);
    Vue.component(Component.name, Component);
    vxeAddRender(componentName, Component);
    vxeAddRender(Component.name, Component);
};


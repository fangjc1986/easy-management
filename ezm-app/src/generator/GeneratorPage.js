const fs = require('fs');
const beautify = require('js-beautify');

module.exports = class {

    config = require("./GeneratorConfig");
    fields = [];
    model = {};
    name = '';
    pagePath = '';
    Name = '';
    nameCamel = '';
    NameCamel = '';
    dir = '../';
    viewPath = '';
    apiPath = '';
    apiPrefix = '';
    apiRelPath = '';

    constructor(fields, model) {
        this.fields = fields;
        this.model = model;
        this.initNameAndPath();

    }

    initNameAndPath() {
        this.apiPrefix = this.model.apiPrefix;
        this.pagePath = this.model.pagePath.trim().replace(/(^\/)|(\/$)/g, "");
        this.name = this.pagePath.split("/").pop();
        this.Name = this.setInitial(this.name, true);
        this.nameCamel = this.toCamel(this.name);
        this.NameCamel = this.setInitial(this.nameCamel, true);
        this.viewPath = this.dir + 'views/' + this.pagePath;
        let p = this.pagePath.split('/');
        p.pop();
        this.apiPath = this.dir + 'api/' + p.join('/');
        this.apiRelPath = this.pagePath;
        if (!fs.existsSync(this.viewPath)) this.mkDir(this.viewPath);
        if (!fs.existsSync(this.apiPath)) this.mkDir(this.apiPath);

    }

    generatorPage() {
        let fp = this.viewPath + '/' + this.name + '.vue';
        if (fs.existsSync(fp)) return;
        let file = fs.readFileSync("./templ/page.templ.vue", 'utf-8');
        file = this.replaceNormalInfo(file);
        fs.writeFileSync(fp, file);
        this.printWrite(fp);
    }

    generatorEdit() {
        let fp = this.viewPath + '/' + this.name + '-edit.vue';
        if (fs.existsSync(fp)) return;
        let file = fs.readFileSync("./templ/edit.templ.vue", 'utf-8');
        file = this.replaceNormalInfo(file);
        fs.writeFileSync(fp, file);
        this.printWrite(fp);
    }

    generatorFilter() {
        let fp = this.viewPath + '/' + this.name + '-filter.vue';
        if (fs.existsSync(fp)) return;
        let file = fs.readFileSync("./templ/filter.templ.vue", 'utf-8');
        file = this.replaceNormalInfo(file);
        fs.writeFileSync(fp, file);
        this.printWrite(fp);
    }

    generatorApi() {
        let fp = this.apiPath + '/' + this.name + '.js';
        if (fs.existsSync(fp)) return;
        let file = fs.readFileSync("./templ/api.templ.jst", 'utf-8');
        file = this.replaceNormalInfo(file);
        file = beautify.js(file, {indent_size: 4, space_in_empty_paren: true});
        fs.writeFileSync(fp, file);
        this.printWrite(fp);
    }

    generatorConfigMixin() {
        let fp = this.viewPath + '/' + this.NameCamel + 'ConfigMixin.js';
        if (fs.existsSync(fp)) return;
        let file = fs.readFileSync("./templ/ConfigMixin.templ.jst", 'utf-8');
        let field = fs.readFileSync("./templ/fields/field.templ.jst", 'utf-8');
        let fieldsString = this.fields.reduce((t, f, i) => {
            if (this.config.excludeFields.includes(f.COLUMN_NAME)) return t;
            const comment = f.COLUMN_COMMENT.replace(/\s/g, '');
            const prop = this.toCamel(f.COLUMN_NAME);
            t.push(
                field.replace(/\@\{label\}/g, comment || prop)
                    .replace(/\@\{prop\}/g, prop)
                    .replace(/\@\{comment\}/g, comment)
                    .replace(/\@\{filterRank\}/g, i + '')
                    .replace(/\@\{editRank\}/g, i + '')
            );
            return t;
        }, []);
        file = file.replace(/\@\{fields\}/g, fieldsString.join('\n'));
        file = beautify.js(file, {indent_size: 4, space_in_empty_paren: true})
        fs.writeFileSync(fp, file);
        this.printWrite(fp);
    }

    generatorSelector() {
        let fp = this.viewPath + '/' + this.name + '-selector.vue';
        if (fs.existsSync(fp)) return;
        let file = fs.readFileSync("./templ/selector.templ.vue", 'utf-8');
        file = this.replaceNormalInfo(file);
        fs.writeFileSync(fp, file);
        this.printWrite(fp);
    }

    replaceNormalInfo(file = '') {
        return file.replace(/\@\{name\}/g, this.name)
            .replace(/\@\{Name\}/g, this.Name)
            .replace(/\@\{nameCamel\}/g, this.nameCamel)
            .replace(/\@\{NameCamel\}/g, this.NameCamel)
            .replace(/\@\{apiPath\}/g, this.apiRelPath)
            .replace(/\@\{pagePath\}/g, this.pagePath)
            .replace(/\@\{apiPrefix\}/g, this.apiPrefix)
    }

    printWrite(f) {
        console.log('成功创建文件：' + f);
    }

    mkDir(dir) {
        let dirs = dir.split('/');
        dirs.reduce((t, d) => {
            if (['.', '..'].includes(d)) {
                t.push(d);
                return t;
            }
            t.push(d);
            if (!fs.existsSync(t.join('/'))) {
                fs.mkdirSync(t.join('/'));
            }
            return t;
        }, [])
    }

    setInitial(str, capital = true) {
        if (capital) {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        } else {
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }

    }

    toCamel(str) {
        return str.replace(/([^_])(?:_+([^_]))/g, function ($0, $1, $2) {
            return $1 + $2.toUpperCase();
        });
    }
};
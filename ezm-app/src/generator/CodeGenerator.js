let Mysql = require("./MysqlUtil");
let Config = require("./GeneratorConfig");
let Generator = require("./GeneratorPage");

const schema = 'ezmpro';

for (let model of Config.models) {
    let mysql = new Mysql();
    mysql.connection.query(
        `select * from information_schema.COLUMNS where table_name = '${model.tableName}' and table_schema = '${schema}'`,
        (error, results, fields) => {
            let generator = new Generator(results, model);
            generator.generatorPage();
            generator.generatorApi();
            generator.generatorEdit();
            generator.generatorFilter();
            generator.generatorConfigMixin();
            generator.generatorSelector();
            mysql.connection.end();
        }
    );
}




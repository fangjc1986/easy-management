let mysql = require('mysql');

let Mysql = class {
    connection = null;

    constructor() {
        this.connection = mysql.createConnection({
            host: '',
            user: '',
            password: '',
            database: ''
        });
    }

};

module.exports = Mysql;


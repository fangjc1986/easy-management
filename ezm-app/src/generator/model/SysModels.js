module.exports = [
    {
        tableName: 'sys_dict',
        apiPrefix: 'sys/dict',
        pagePath: 'base/dict',
    },
    {
        tableName: 'sys_dict_detail',
        apiPrefix: 'sys/dict-detail',
        pagePath: 'base/dict_detail',
    },
    {
        tableName: 'sys_config',
        apiPrefix: 'sys/config',
        pagePath: 'base/config',
    },
    {
        tableName: 'sys_auth',
        apiPrefix: 'sys/auth',
        pagePath: 'user_power/auth',
    },
    {
        tableName: 'sys_role',
        apiPrefix: 'sys/role',
        pagePath: 'user_power/role',
    },
    {
        tableName: 'sys_user',
        apiPrefix: 'sys/user',
        pagePath: 'user_power/user',
    },
    {
        tableName: 'sys_tenant',
        apiPrefix: 'sys/tenant',
        pagePath: 'user_power/tenant',
    },
    {
        tableName: 'sys_account_log',
        apiPrefix: 'sys/account-log',
        pagePath: 'log/account_log',
    },
    {
        tableName: 'sys_request_log',
        apiPrefix: 'sys/request-log',
        pagePath: 'log/request_log',
    },
];
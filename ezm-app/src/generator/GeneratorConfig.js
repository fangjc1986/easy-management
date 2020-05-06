module.exports = {
    excludeFields: ['id', 'tenant_id', 'create_user', 'create_time', 'update_time', 'update_user'],
    models: [
        ...require('./model/SysModels'),
        ...require('./model/WmsModels'),
    ],
};
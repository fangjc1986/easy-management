import Vue from 'vue'
import 'xe-utils'
import VXETable from 'vxe-table'
import 'vxe-table/lib/index.css'
import VXETablePluginElement from 'vxe-table-plugin-element'
import 'vxe-table-plugin-element/dist/style.css'

VXETable.setup({
    size: 'mini',
});
Vue.use(VXETable);
VXETable.use(VXETablePluginElement);

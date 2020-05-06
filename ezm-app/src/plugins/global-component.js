import EzmTextInput from '@/components/editor/ezm-text-input'
import EzmSwitch from '@/components/editor/ezm-switch'
import EzmColorPicker from '@/components/selector/ezm-color-picker'
import EzmOptionsTag from '@/components/display/ezm-options-tag'
import EzmOptionsSelector from '@/components/selector/ezm-options-selector';
import EzmFroalaEditor from '@/components/editor/ezm-froala-editor';
import EzmTreeSelector from '@/components/selector/ezm-tree-selector';
import EzmDictSelector from '@/components/selector/ezm-dict-selector';
import EzmInput from '@/components/editor/ezm-input';

import {vxeTableAddRender} from "@/utils/vue.render.util";

//自定义组件名
vxeTableAddRender(EzmTextInput);
vxeTableAddRender(EzmColorPicker);
vxeTableAddRender(EzmOptionsTag);
vxeTableAddRender(EzmSwitch);
vxeTableAddRender(EzmOptionsSelector);
vxeTableAddRender(EzmFroalaEditor);
vxeTableAddRender(EzmTreeSelector);
vxeTableAddRender(EzmInput);
vxeTableAddRender(EzmDictSelector);



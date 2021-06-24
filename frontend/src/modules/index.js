import { combineReducers } from 'redux';
import block from './block';
import challenge from './challenge';
import user from './user';

// 여러 리듀서를 합쳐서 rootReducer에 선언
const rootReducer = combineReducers({ 
    user,
    block,
    challenge,
})
export default rootReducer;
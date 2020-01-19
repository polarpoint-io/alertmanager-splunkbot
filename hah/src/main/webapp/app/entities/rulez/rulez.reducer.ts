import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRulez, defaultValue } from 'app/shared/model/rulez.model';

export const ACTION_TYPES = {
  FETCH_RULEZ_LIST: 'rulez/FETCH_RULEZ_LIST',
  FETCH_RULEZ: 'rulez/FETCH_RULEZ',
  CREATE_RULEZ: 'rulez/CREATE_RULEZ',
  UPDATE_RULEZ: 'rulez/UPDATE_RULEZ',
  DELETE_RULEZ: 'rulez/DELETE_RULEZ',
  RESET: 'rulez/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRulez>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type RulezState = Readonly<typeof initialState>;

// Reducer

export default (state: RulezState = initialState, action): RulezState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RULEZ_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RULEZ):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RULEZ):
    case REQUEST(ACTION_TYPES.UPDATE_RULEZ):
    case REQUEST(ACTION_TYPES.DELETE_RULEZ):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RULEZ_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RULEZ):
    case FAILURE(ACTION_TYPES.CREATE_RULEZ):
    case FAILURE(ACTION_TYPES.UPDATE_RULEZ):
    case FAILURE(ACTION_TYPES.DELETE_RULEZ):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RULEZ_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_RULEZ):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RULEZ):
    case SUCCESS(ACTION_TYPES.UPDATE_RULEZ):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RULEZ):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/rulezs';

// Actions

export const getEntities: ICrudGetAllAction<IRulez> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RULEZ_LIST,
  payload: axios.get<IRulez>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IRulez> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RULEZ,
    payload: axios.get<IRulez>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRulez> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RULEZ,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRulez> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RULEZ,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRulez> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RULEZ,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

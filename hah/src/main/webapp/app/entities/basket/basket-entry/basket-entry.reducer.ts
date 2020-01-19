import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { IBasketEntry, defaultValue } from 'app/shared/model/basket/basket-entry.model';

export const ACTION_TYPES = {
  FETCH_BASKETENTRY_LIST: 'basketEntry/FETCH_BASKETENTRY_LIST',
  FETCH_BASKETENTRY: 'basketEntry/FETCH_BASKETENTRY',
  CREATE_BASKETENTRY: 'basketEntry/CREATE_BASKETENTRY',
  UPDATE_BASKETENTRY: 'basketEntry/UPDATE_BASKETENTRY',
  DELETE_BASKETENTRY: 'basketEntry/DELETE_BASKETENTRY',
  RESET: 'basketEntry/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBasketEntry>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type BasketEntryState = Readonly<typeof initialState>;

// Reducer

export default (state: BasketEntryState = initialState, action): BasketEntryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BASKETENTRY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BASKETENTRY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BASKETENTRY):
    case REQUEST(ACTION_TYPES.UPDATE_BASKETENTRY):
    case REQUEST(ACTION_TYPES.DELETE_BASKETENTRY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BASKETENTRY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BASKETENTRY):
    case FAILURE(ACTION_TYPES.CREATE_BASKETENTRY):
    case FAILURE(ACTION_TYPES.UPDATE_BASKETENTRY):
    case FAILURE(ACTION_TYPES.DELETE_BASKETENTRY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BASKETENTRY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_BASKETENTRY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BASKETENTRY):
    case SUCCESS(ACTION_TYPES.UPDATE_BASKETENTRY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BASKETENTRY):
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

const apiUrl = 'services/basket/api/basket-entries';

// Actions

export const getEntities: ICrudGetAllAction<IBasketEntry> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BASKETENTRY_LIST,
    payload: axios.get<IBasketEntry>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IBasketEntry> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BASKETENTRY,
    payload: axios.get<IBasketEntry>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBasketEntry> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BASKETENTRY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBasketEntry> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BASKETENTRY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBasketEntry> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BASKETENTRY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

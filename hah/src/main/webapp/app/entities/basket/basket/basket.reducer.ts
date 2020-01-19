import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { IBasket, defaultValue } from 'app/shared/model/basket/basket.model';

export const ACTION_TYPES = {
  FETCH_BASKET_LIST: 'basket/FETCH_BASKET_LIST',
  FETCH_BASKET: 'basket/FETCH_BASKET',
  CREATE_BASKET: 'basket/CREATE_BASKET',
  UPDATE_BASKET: 'basket/UPDATE_BASKET',
  DELETE_BASKET: 'basket/DELETE_BASKET',
  RESET: 'basket/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBasket>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type BasketState = Readonly<typeof initialState>;

// Reducer

export default (state: BasketState = initialState, action): BasketState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BASKET_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BASKET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BASKET):
    case REQUEST(ACTION_TYPES.UPDATE_BASKET):
    case REQUEST(ACTION_TYPES.DELETE_BASKET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BASKET_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BASKET):
    case FAILURE(ACTION_TYPES.CREATE_BASKET):
    case FAILURE(ACTION_TYPES.UPDATE_BASKET):
    case FAILURE(ACTION_TYPES.DELETE_BASKET):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BASKET_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_BASKET):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BASKET):
    case SUCCESS(ACTION_TYPES.UPDATE_BASKET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BASKET):
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

const apiUrl = 'services/basket/api/baskets';

// Actions

export const getEntities: ICrudGetAllAction<IBasket> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BASKET_LIST,
    payload: axios.get<IBasket>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IBasket> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BASKET,
    payload: axios.get<IBasket>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBasket> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BASKET,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBasket> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BASKET,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBasket> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BASKET,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVatRate, defaultValue } from 'app/shared/model/vat-rate.model';

export const ACTION_TYPES = {
  FETCH_VATRATE_LIST: 'vatRate/FETCH_VATRATE_LIST',
  FETCH_VATRATE: 'vatRate/FETCH_VATRATE',
  CREATE_VATRATE: 'vatRate/CREATE_VATRATE',
  UPDATE_VATRATE: 'vatRate/UPDATE_VATRATE',
  DELETE_VATRATE: 'vatRate/DELETE_VATRATE',
  RESET: 'vatRate/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVatRate>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type VatRateState = Readonly<typeof initialState>;

// Reducer

export default (state: VatRateState = initialState, action): VatRateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VATRATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VATRATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VATRATE):
    case REQUEST(ACTION_TYPES.UPDATE_VATRATE):
    case REQUEST(ACTION_TYPES.DELETE_VATRATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VATRATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VATRATE):
    case FAILURE(ACTION_TYPES.CREATE_VATRATE):
    case FAILURE(ACTION_TYPES.UPDATE_VATRATE):
    case FAILURE(ACTION_TYPES.DELETE_VATRATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VATRATE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VATRATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VATRATE):
    case SUCCESS(ACTION_TYPES.UPDATE_VATRATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VATRATE):
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

const apiUrl = 'api/vat-rates';

// Actions

export const getEntities: ICrudGetAllAction<IVatRate> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VATRATE_LIST,
  payload: axios.get<IVatRate>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IVatRate> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VATRATE,
    payload: axios.get<IVatRate>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVatRate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VATRATE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVatRate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VATRATE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVatRate> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VATRATE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

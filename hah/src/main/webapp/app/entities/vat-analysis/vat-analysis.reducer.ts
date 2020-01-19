import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVatAnalysis, defaultValue } from 'app/shared/model/vat-analysis.model';

export const ACTION_TYPES = {
  FETCH_VATANALYSIS_LIST: 'vatAnalysis/FETCH_VATANALYSIS_LIST',
  FETCH_VATANALYSIS: 'vatAnalysis/FETCH_VATANALYSIS',
  CREATE_VATANALYSIS: 'vatAnalysis/CREATE_VATANALYSIS',
  UPDATE_VATANALYSIS: 'vatAnalysis/UPDATE_VATANALYSIS',
  DELETE_VATANALYSIS: 'vatAnalysis/DELETE_VATANALYSIS',
  RESET: 'vatAnalysis/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVatAnalysis>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type VatAnalysisState = Readonly<typeof initialState>;

// Reducer

export default (state: VatAnalysisState = initialState, action): VatAnalysisState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VATANALYSIS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VATANALYSIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VATANALYSIS):
    case REQUEST(ACTION_TYPES.UPDATE_VATANALYSIS):
    case REQUEST(ACTION_TYPES.DELETE_VATANALYSIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VATANALYSIS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VATANALYSIS):
    case FAILURE(ACTION_TYPES.CREATE_VATANALYSIS):
    case FAILURE(ACTION_TYPES.UPDATE_VATANALYSIS):
    case FAILURE(ACTION_TYPES.DELETE_VATANALYSIS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VATANALYSIS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VATANALYSIS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VATANALYSIS):
    case SUCCESS(ACTION_TYPES.UPDATE_VATANALYSIS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VATANALYSIS):
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

const apiUrl = 'api/vat-analyses';

// Actions

export const getEntities: ICrudGetAllAction<IVatAnalysis> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VATANALYSIS_LIST,
  payload: axios.get<IVatAnalysis>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IVatAnalysis> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VATANALYSIS,
    payload: axios.get<IVatAnalysis>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVatAnalysis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VATANALYSIS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVatAnalysis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VATANALYSIS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVatAnalysis> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VATANALYSIS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

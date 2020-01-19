import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISubscriptionGroup, defaultValue } from 'app/shared/model/subscription-group.model';

export const ACTION_TYPES = {
  FETCH_SUBSCRIPTIONGROUP_LIST: 'subscriptionGroup/FETCH_SUBSCRIPTIONGROUP_LIST',
  FETCH_SUBSCRIPTIONGROUP: 'subscriptionGroup/FETCH_SUBSCRIPTIONGROUP',
  CREATE_SUBSCRIPTIONGROUP: 'subscriptionGroup/CREATE_SUBSCRIPTIONGROUP',
  UPDATE_SUBSCRIPTIONGROUP: 'subscriptionGroup/UPDATE_SUBSCRIPTIONGROUP',
  DELETE_SUBSCRIPTIONGROUP: 'subscriptionGroup/DELETE_SUBSCRIPTIONGROUP',
  RESET: 'subscriptionGroup/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISubscriptionGroup>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type SubscriptionGroupState = Readonly<typeof initialState>;

// Reducer

export default (state: SubscriptionGroupState = initialState, action): SubscriptionGroupState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SUBSCRIPTIONGROUP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SUBSCRIPTIONGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SUBSCRIPTIONGROUP):
    case REQUEST(ACTION_TYPES.UPDATE_SUBSCRIPTIONGROUP):
    case REQUEST(ACTION_TYPES.DELETE_SUBSCRIPTIONGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SUBSCRIPTIONGROUP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SUBSCRIPTIONGROUP):
    case FAILURE(ACTION_TYPES.CREATE_SUBSCRIPTIONGROUP):
    case FAILURE(ACTION_TYPES.UPDATE_SUBSCRIPTIONGROUP):
    case FAILURE(ACTION_TYPES.DELETE_SUBSCRIPTIONGROUP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUBSCRIPTIONGROUP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUBSCRIPTIONGROUP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SUBSCRIPTIONGROUP):
    case SUCCESS(ACTION_TYPES.UPDATE_SUBSCRIPTIONGROUP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SUBSCRIPTIONGROUP):
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

const apiUrl = 'api/subscription-groups';

// Actions

export const getEntities: ICrudGetAllAction<ISubscriptionGroup> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SUBSCRIPTIONGROUP_LIST,
  payload: axios.get<ISubscriptionGroup>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ISubscriptionGroup> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SUBSCRIPTIONGROUP,
    payload: axios.get<ISubscriptionGroup>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISubscriptionGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SUBSCRIPTIONGROUP,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISubscriptionGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SUBSCRIPTIONGROUP,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISubscriptionGroup> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SUBSCRIPTIONGROUP,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

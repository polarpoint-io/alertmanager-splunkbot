import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Basket from './basket';
import BasketDetail from './basket-detail';
import BasketUpdate from './basket-update';
import BasketDeleteDialog from './basket-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BasketDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BasketUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BasketUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BasketDetail} />
      <ErrorBoundaryRoute path={match.url} component={Basket} />
    </Switch>
  </>
);

export default Routes;

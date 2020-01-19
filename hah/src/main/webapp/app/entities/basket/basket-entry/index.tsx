import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BasketEntry from './basket-entry';
import BasketEntryDetail from './basket-entry-detail';
import BasketEntryUpdate from './basket-entry-update';
import BasketEntryDeleteDialog from './basket-entry-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BasketEntryDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BasketEntryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BasketEntryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BasketEntryDetail} />
      <ErrorBoundaryRoute path={match.url} component={BasketEntry} />
    </Switch>
  </>
);

export default Routes;

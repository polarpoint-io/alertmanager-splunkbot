import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VatRate from './vat-rate';
import VatRateDetail from './vat-rate-detail';
import VatRateUpdate from './vat-rate-update';
import VatRateDeleteDialog from './vat-rate-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VatRateDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VatRateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VatRateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VatRateDetail} />
      <ErrorBoundaryRoute path={match.url} component={VatRate} />
    </Switch>
  </>
);

export default Routes;

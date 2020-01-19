import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Rulez from './rulez';
import RulezDetail from './rulez-detail';
import RulezUpdate from './rulez-update';
import RulezDeleteDialog from './rulez-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RulezDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RulezUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RulezUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RulezDetail} />
      <ErrorBoundaryRoute path={match.url} component={Rulez} />
    </Switch>
  </>
);

export default Routes;

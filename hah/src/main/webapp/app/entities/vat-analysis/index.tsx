import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VatAnalysis from './vat-analysis';
import VatAnalysisDetail from './vat-analysis-detail';
import VatAnalysisUpdate from './vat-analysis-update';
import VatAnalysisDeleteDialog from './vat-analysis-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VatAnalysisDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VatAnalysisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VatAnalysisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VatAnalysisDetail} />
      <ErrorBoundaryRoute path={match.url} component={VatAnalysis} />
    </Switch>
  </>
);

export default Routes;

import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Branch from './branch';
import SubscriptionGroup from './subscription-group';
import Category from './product/category';
import Product from './product/product';
import Token from './token';
import Rulez from './rulez';
import VatRate from './vat-rate';
import Field from './product/field';
import Page from './product/page';
import Basket from './basket/basket';
import BasketEntry from './basket/basket-entry';
import VatAnalysis from './vat-analysis';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}branch`} component={Branch} />
      <ErrorBoundaryRoute path={`${match.url}subscription-group`} component={SubscriptionGroup} />
      <ErrorBoundaryRoute path={`${match.url}category`} component={Category} />
      <ErrorBoundaryRoute path={`${match.url}product`} component={Product} />
      <ErrorBoundaryRoute path={`${match.url}token`} component={Token} />
      <ErrorBoundaryRoute path={`${match.url}rulez`} component={Rulez} />
      <ErrorBoundaryRoute path={`${match.url}vat-rate`} component={VatRate} />
      <ErrorBoundaryRoute path={`${match.url}field`} component={Field} />
      <ErrorBoundaryRoute path={`${match.url}page`} component={Page} />
      <ErrorBoundaryRoute path={`${match.url}basket`} component={Basket} />
      <ErrorBoundaryRoute path={`${match.url}basket-entry`} component={BasketEntry} />
      <ErrorBoundaryRoute path={`${match.url}vat-analysis`} component={VatAnalysis} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;

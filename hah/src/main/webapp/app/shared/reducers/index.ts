import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import branch, {
  BranchState
} from 'app/entities/branch/branch.reducer';
// prettier-ignore
import subscriptionGroup, {
  SubscriptionGroupState
} from 'app/entities/subscription-group/subscription-group.reducer';
// prettier-ignore
import category, {
  CategoryState
} from 'app/entities/product/category/category.reducer';
// prettier-ignore
import product, {
  ProductState
} from 'app/entities/product/product/product.reducer';
// prettier-ignore
import token, {
  TokenState
} from 'app/entities/token/token.reducer';
// prettier-ignore
import rulez, {
  RulezState
} from 'app/entities/rulez/rulez.reducer';
// prettier-ignore
import vatRate, {
  VatRateState
} from 'app/entities/vat-rate/vat-rate.reducer';
// prettier-ignore
import field, {
  FieldState
} from 'app/entities/product/field/field.reducer';
// prettier-ignore
import page, {
  PageState
} from 'app/entities/product/page/page.reducer';
// prettier-ignore
import basket, {
  BasketState
} from 'app/entities/basket/basket/basket.reducer';
// prettier-ignore
import basketEntry, {
  BasketEntryState
} from 'app/entities/basket/basket-entry/basket-entry.reducer';
// prettier-ignore
import vatAnalysis, {
  VatAnalysisState
} from 'app/entities/vat-analysis/vat-analysis.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly branch: BranchState;
  readonly subscriptionGroup: SubscriptionGroupState;
  readonly category: CategoryState;
  readonly product: ProductState;
  readonly token: TokenState;
  readonly rulez: RulezState;
  readonly vatRate: VatRateState;
  readonly field: FieldState;
  readonly page: PageState;
  readonly basket: BasketState;
  readonly basketEntry: BasketEntryState;
  readonly vatAnalysis: VatAnalysisState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  branch,
  subscriptionGroup,
  category,
  product,
  token,
  rulez,
  vatRate,
  field,
  page,
  basket,
  basketEntry,
  vatAnalysis,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;

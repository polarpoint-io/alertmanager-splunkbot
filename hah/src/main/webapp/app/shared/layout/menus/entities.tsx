import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <MenuItem icon="asterisk" to="/branch">
      <Translate contentKey="global.menu.entities.branch" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/subscription-group">
      <Translate contentKey="global.menu.entities.subscriptionGroup" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/category">
      <Translate contentKey="global.menu.entities.productCategory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/product">
      <Translate contentKey="global.menu.entities.productProduct" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/token">
      <Translate contentKey="global.menu.entities.token" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/rulez">
      <Translate contentKey="global.menu.entities.rulez" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/vat-rate">
      <Translate contentKey="global.menu.entities.vatRate" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/field">
      <Translate contentKey="global.menu.entities.productField" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/page">
      <Translate contentKey="global.menu.entities.productPage" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/basket">
      <Translate contentKey="global.menu.entities.basketBasket" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/basket-entry">
      <Translate contentKey="global.menu.entities.basketBasketEntry" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/vat-analysis">
      <Translate contentKey="global.menu.entities.vatAnalysis" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);

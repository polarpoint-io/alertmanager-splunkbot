import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './basket-entry.reducer';
import { IBasketEntry } from 'app/shared/model/basket/basket-entry.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBasketEntryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BasketEntryDetail = (props: IBasketEntryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { basketEntryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="hahApp.basketBasketEntry.detail.title">BasketEntry</Translate> [<b>{basketEntryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="basketId">
              <Translate contentKey="hahApp.basketBasketEntry.basketId">Basket Id</Translate>
            </span>
          </dt>
          <dd>{basketEntryEntity.basketId}</dd>
          <dt>
            <span id="canEdit">
              <Translate contentKey="hahApp.basketBasketEntry.canEdit">Can Edit</Translate>
            </span>
          </dt>
          <dd>{basketEntryEntity.canEdit ? 'true' : 'false'}</dd>
          <dt>
            <span id="prodictId">
              <Translate contentKey="hahApp.basketBasketEntry.prodictId">Prodict Id</Translate>
            </span>
          </dt>
          <dd>{basketEntryEntity.prodictId}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="hahApp.basketBasketEntry.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{basketEntryEntity.quantity}</dd>
          <dt>
            <span id="refundable">
              <Translate contentKey="hahApp.basketBasketEntry.refundable">Refundable</Translate>
            </span>
          </dt>
          <dd>{basketEntryEntity.refundable ? 'true' : 'false'}</dd>
          <dt>
            <span id="removeable">
              <Translate contentKey="hahApp.basketBasketEntry.removeable">Removeable</Translate>
            </span>
          </dt>
          <dd>{basketEntryEntity.removeable ? 'true' : 'false'}</dd>
          <dt>
            <span id="totalPrice">
              <Translate contentKey="hahApp.basketBasketEntry.totalPrice">Total Price</Translate>
            </span>
          </dt>
          <dd>{basketEntryEntity.totalPrice}</dd>
          <dt>
            <span id="transactionId">
              <Translate contentKey="hahApp.basketBasketEntry.transactionId">Transaction Id</Translate>
            </span>
          </dt>
          <dd>{basketEntryEntity.transactionId}</dd>
          <dt>
            <span id="unitPrice">
              <Translate contentKey="hahApp.basketBasketEntry.unitPrice">Unit Price</Translate>
            </span>
          </dt>
          <dd>{basketEntryEntity.unitPrice}</dd>
          <dt>
            <span id="vatCode">
              <Translate contentKey="hahApp.basketBasketEntry.vatCode">Vat Code</Translate>
            </span>
          </dt>
          <dd>{basketEntryEntity.vatCode}</dd>
          <dt>
            <span id="vatElement">
              <Translate contentKey="hahApp.basketBasketEntry.vatElement">Vat Element</Translate>
            </span>
          </dt>
          <dd>{basketEntryEntity.vatElement}</dd>
          <dt>
            <Translate contentKey="hahApp.basketBasketEntry.basket">Basket</Translate>
          </dt>
          <dd>{basketEntryEntity.basket ? basketEntryEntity.basket.basketId : ''}</dd>
        </dl>
        <Button tag={Link} to="/basket-entry" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/basket-entry/${basketEntryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ basketEntry }: IRootState) => ({
  basketEntryEntity: basketEntry.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BasketEntryDetail);

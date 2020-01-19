import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './basket.reducer';
import { IBasket } from 'app/shared/model/basket/basket.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBasketDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BasketDetail = (props: IBasketDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { basketEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="hahApp.basketBasket.detail.title">Basket</Translate> [<b>{basketEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="basketId">
              <Translate contentKey="hahApp.basketBasket.basketId">Basket Id</Translate>
            </span>
          </dt>
          <dd>{basketEntity.basketId}</dd>
          <dt>
            <span id="totalPrice">
              <Translate contentKey="hahApp.basketBasket.totalPrice">Total Price</Translate>
            </span>
          </dt>
          <dd>{basketEntity.totalPrice}</dd>
          <dt>
            <span id="txns">
              <Translate contentKey="hahApp.basketBasket.txns">Txns</Translate>
            </span>
          </dt>
          <dd>{basketEntity.txns}</dd>
          <dt>
            <span id="vatAnalysis">
              <Translate contentKey="hahApp.basketBasket.vatAnalysis">Vat Analysis</Translate>
            </span>
          </dt>
          <dd>{basketEntity.vatAnalysis}</dd>
        </dl>
        <Button tag={Link} to="/basket" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/basket/${basketEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ basket }: IRootState) => ({
  basketEntity: basket.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BasketDetail);

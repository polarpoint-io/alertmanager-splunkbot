import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './basket.reducer';
import { IBasket } from 'app/shared/model/basket/basket.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBasketUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BasketUpdate = (props: IBasketUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { basketEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/basket' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...basketEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hahApp.basketBasket.home.createOrEditLabel">
            <Translate contentKey="hahApp.basketBasket.home.createOrEditLabel">Create or edit a Basket</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : basketEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="basket-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="basket-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="basketIdLabel" for="basket-basketId">
                  <Translate contentKey="hahApp.basketBasket.basketId">Basket Id</Translate>
                </Label>
                <AvField
                  id="basket-basketId"
                  type="text"
                  name="basketId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="totalPriceLabel" for="basket-totalPrice">
                  <Translate contentKey="hahApp.basketBasket.totalPrice">Total Price</Translate>
                </Label>
                <AvField
                  id="basket-totalPrice"
                  type="text"
                  name="totalPrice"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="txnsLabel" for="basket-txns">
                  <Translate contentKey="hahApp.basketBasket.txns">Txns</Translate>
                </Label>
                <AvField id="basket-txns" type="text" name="txns" />
              </AvGroup>
              <AvGroup>
                <Label id="vatAnalysisLabel" for="basket-vatAnalysis">
                  <Translate contentKey="hahApp.basketBasket.vatAnalysis">Vat Analysis</Translate>
                </Label>
                <AvField id="basket-vatAnalysis" type="text" name="vatAnalysis" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/basket" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  basketEntity: storeState.basket.entity,
  loading: storeState.basket.loading,
  updating: storeState.basket.updating,
  updateSuccess: storeState.basket.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BasketUpdate);

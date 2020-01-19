import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBasket } from 'app/shared/model/basket/basket.model';
import { getEntities as getBaskets } from 'app/entities/basket/basket/basket.reducer';
import { getEntity, updateEntity, createEntity, reset } from './basket-entry.reducer';
import { IBasketEntry } from 'app/shared/model/basket/basket-entry.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBasketEntryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BasketEntryUpdate = (props: IBasketEntryUpdateProps) => {
  const [basketId, setBasketId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { basketEntryEntity, baskets, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/basket-entry' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getBaskets();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...basketEntryEntity,
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
          <h2 id="hahApp.basketBasketEntry.home.createOrEditLabel">
            <Translate contentKey="hahApp.basketBasketEntry.home.createOrEditLabel">Create or edit a BasketEntry</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : basketEntryEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="basket-entry-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="basket-entry-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="basketIdLabel" for="basket-entry-basketId">
                  <Translate contentKey="hahApp.basketBasketEntry.basketId">Basket Id</Translate>
                </Label>
                <AvField id="basket-entry-basketId" type="text" name="basketId" />
              </AvGroup>
              <AvGroup check>
                <Label id="canEditLabel">
                  <AvInput id="basket-entry-canEdit" type="checkbox" className="form-check-input" name="canEdit" />
                  <Translate contentKey="hahApp.basketBasketEntry.canEdit">Can Edit</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="prodictIdLabel" for="basket-entry-prodictId">
                  <Translate contentKey="hahApp.basketBasketEntry.prodictId">Prodict Id</Translate>
                </Label>
                <AvField id="basket-entry-prodictId" type="string" className="form-control" name="prodictId" />
              </AvGroup>
              <AvGroup>
                <Label id="quantityLabel" for="basket-entry-quantity">
                  <Translate contentKey="hahApp.basketBasketEntry.quantity">Quantity</Translate>
                </Label>
                <AvField id="basket-entry-quantity" type="string" className="form-control" name="quantity" />
              </AvGroup>
              <AvGroup check>
                <Label id="refundableLabel">
                  <AvInput id="basket-entry-refundable" type="checkbox" className="form-check-input" name="refundable" />
                  <Translate contentKey="hahApp.basketBasketEntry.refundable">Refundable</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="removeableLabel">
                  <AvInput id="basket-entry-removeable" type="checkbox" className="form-check-input" name="removeable" />
                  <Translate contentKey="hahApp.basketBasketEntry.removeable">Removeable</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="totalPriceLabel" for="basket-entry-totalPrice">
                  <Translate contentKey="hahApp.basketBasketEntry.totalPrice">Total Price</Translate>
                </Label>
                <AvField
                  id="basket-entry-totalPrice"
                  type="text"
                  name="totalPrice"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="transactionIdLabel" for="basket-entry-transactionId">
                  <Translate contentKey="hahApp.basketBasketEntry.transactionId">Transaction Id</Translate>
                </Label>
                <AvField id="basket-entry-transactionId" type="text" name="transactionId" />
              </AvGroup>
              <AvGroup>
                <Label id="unitPriceLabel" for="basket-entry-unitPrice">
                  <Translate contentKey="hahApp.basketBasketEntry.unitPrice">Unit Price</Translate>
                </Label>
                <AvField id="basket-entry-unitPrice" type="text" name="unitPrice" />
              </AvGroup>
              <AvGroup>
                <Label id="vatCodeLabel" for="basket-entry-vatCode">
                  <Translate contentKey="hahApp.basketBasketEntry.vatCode">Vat Code</Translate>
                </Label>
                <AvField id="basket-entry-vatCode" type="text" name="vatCode" />
              </AvGroup>
              <AvGroup>
                <Label id="vatElementLabel" for="basket-entry-vatElement">
                  <Translate contentKey="hahApp.basketBasketEntry.vatElement">Vat Element</Translate>
                </Label>
                <AvField id="basket-entry-vatElement" type="text" name="vatElement" />
              </AvGroup>
              <AvGroup>
                <Label for="basket-entry-basket">
                  <Translate contentKey="hahApp.basketBasketEntry.basket">Basket</Translate>
                </Label>
                <AvInput
                  id="basket-entry-basket"
                  type="select"
                  className="form-control"
                  name="basket.id"
                  value={isNew ? baskets[0] && baskets[0].id : basketEntryEntity.basket.id}
                  required
                >
                  {baskets
                    ? baskets.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.basketId}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/basket-entry" replace color="info">
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
  baskets: storeState.basket.entities,
  basketEntryEntity: storeState.basketEntry.entity,
  loading: storeState.basketEntry.loading,
  updating: storeState.basketEntry.updating,
  updateSuccess: storeState.basketEntry.updateSuccess
});

const mapDispatchToProps = {
  getBaskets,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BasketEntryUpdate);

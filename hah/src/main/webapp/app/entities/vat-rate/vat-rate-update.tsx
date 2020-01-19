import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProduct } from 'app/shared/model/product/product.model';
import { getEntities as getProducts } from 'app/entities/product/product/product.reducer';
import { getEntity, updateEntity, createEntity, reset } from './vat-rate.reducer';
import { IVatRate } from 'app/shared/model/vat-rate.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVatRateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VatRateUpdate = (props: IVatRateUpdateProps) => {
  const [vatRateId, setVatRateId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { vatRateEntity, products, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/vat-rate');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getProducts();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...vatRateEntity,
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
          <h2 id="hahApp.vatRate.home.createOrEditLabel">
            <Translate contentKey="hahApp.vatRate.home.createOrEditLabel">Create or edit a VatRate</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : vatRateEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="vat-rate-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="vat-rate-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="vat-rate-code">
                  <Translate contentKey="hahApp.vatRate.code">Code</Translate>
                </Label>
                <AvField
                  id="vat-rate-code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="rateLabel" for="vat-rate-rate">
                  <Translate contentKey="hahApp.vatRate.rate">Rate</Translate>
                </Label>
                <AvField id="vat-rate-rate" type="string" className="form-control" name="rate" />
              </AvGroup>
              <AvGroup>
                <Label for="vat-rate-vatRate">
                  <Translate contentKey="hahApp.vatRate.vatRate">Vat Rate</Translate>
                </Label>
                <AvInput
                  id="vat-rate-vatRate"
                  type="select"
                  className="form-control"
                  name="vatRate.id"
                  value={isNew ? products[0] && products[0].id : vatRateEntity.vatRate.id}
                  required
                >
                  {products
                    ? products.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.code}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/vat-rate" replace color="info">
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
  products: storeState.product.entities,
  vatRateEntity: storeState.vatRate.entity,
  loading: storeState.vatRate.loading,
  updating: storeState.vatRate.updating,
  updateSuccess: storeState.vatRate.updateSuccess
});

const mapDispatchToProps = {
  getProducts,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VatRateUpdate);

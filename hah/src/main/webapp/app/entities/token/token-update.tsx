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
import { getEntity, updateEntity, createEntity, reset } from './token.reducer';
import { IToken } from 'app/shared/model/token.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITokenUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TokenUpdate = (props: ITokenUpdateProps) => {
  const [tokenId, setTokenId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { tokenEntity, products, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/token');
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
        ...tokenEntity,
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
          <h2 id="hahApp.token.home.createOrEditLabel">
            <Translate contentKey="hahApp.token.home.createOrEditLabel">Create or edit a Token</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : tokenEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="token-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="token-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="clientAccountNameLabel" for="token-clientAccountName">
                  <Translate contentKey="hahApp.token.clientAccountName">Client Account Name</Translate>
                </Label>
                <AvField
                  id="token-clientAccountName"
                  type="text"
                  name="clientAccountName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="token-clientId">
                  <Translate contentKey="hahApp.token.clientId">Client Id</Translate>
                </Label>
                <AvField id="token-clientId" type="string" className="form-control" name="clientId" />
              </AvGroup>
              <AvGroup>
                <Label id="iinLabel" for="token-iin">
                  <Translate contentKey="hahApp.token.iin">Iin</Translate>
                </Label>
                <AvField id="token-iin" type="string" className="form-control" name="iin" />
              </AvGroup>
              <AvGroup>
                <Label id="itemIdLabel" for="token-itemId">
                  <Translate contentKey="hahApp.token.itemId">Item Id</Translate>
                </Label>
                <AvField id="token-itemId" type="string" className="form-control" name="itemId" />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="token-name">
                  <Translate contentKey="hahApp.token.name">Name</Translate>
                </Label>
                <AvField id="token-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="svcStartLabel" for="token-svcStart">
                  <Translate contentKey="hahApp.token.svcStart">Svc Start</Translate>
                </Label>
                <AvField id="token-svcStart" type="string" className="form-control" name="svcStart" />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="token-type">
                  <Translate contentKey="hahApp.token.type">Type</Translate>
                </Label>
                <AvField id="token-type" type="text" name="type" />
              </AvGroup>
              <AvGroup>
                <Label for="token-token">
                  <Translate contentKey="hahApp.token.token">Token</Translate>
                </Label>
                <AvInput
                  id="token-token"
                  type="select"
                  className="form-control"
                  name="token.id"
                  value={isNew ? products[0] && products[0].id : tokenEntity.token.id}
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
              <Button tag={Link} id="cancel-save" to="/token" replace color="info">
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
  tokenEntity: storeState.token.entity,
  loading: storeState.token.loading,
  updating: storeState.token.updating,
  updateSuccess: storeState.token.updateSuccess
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

export default connect(mapStateToProps, mapDispatchToProps)(TokenUpdate);

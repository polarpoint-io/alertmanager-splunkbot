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
import { IField } from 'app/shared/model/product/field.model';
import { getEntities as getFields } from 'app/entities/product/field/field.reducer';
import { getEntity, updateEntity, createEntity, reset } from './page.reducer';
import { IPage } from 'app/shared/model/product/page.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PageUpdate = (props: IPageUpdateProps) => {
  const [pageId, setPageId] = useState('0');
  const [orderId, setOrderId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { pageEntity, products, fields, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/page' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getProducts();
    props.getFields();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...pageEntity,
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
          <h2 id="hahApp.productPage.home.createOrEditLabel">
            <Translate contentKey="hahApp.productPage.home.createOrEditLabel">Create or edit a Page</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : pageEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="page-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="page-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="numberLabel" for="page-number">
                  <Translate contentKey="hahApp.productPage.number">Number</Translate>
                </Label>
                <AvField id="page-number" type="string" className="form-control" name="number" />
              </AvGroup>
              <AvGroup>
                <Label id="predicatesLabel" for="page-predicates">
                  <Translate contentKey="hahApp.productPage.predicates">Predicates</Translate>
                </Label>
                <AvField id="page-predicates" type="text" name="predicates" />
              </AvGroup>
              <AvGroup>
                <Label id="protectionLevelLabel" for="page-protectionLevel">
                  <Translate contentKey="hahApp.productPage.protectionLevel">Protection Level</Translate>
                </Label>
                <AvField id="page-protectionLevel" type="text" name="protectionLevel" />
              </AvGroup>
              <AvGroup>
                <Label id="quoteLabel" for="page-quote">
                  <Translate contentKey="hahApp.productPage.quote">Quote</Translate>
                </Label>
                <AvField id="page-quote" type="text" name="quote" />
              </AvGroup>
              <AvGroup>
                <Label id="titleLabel" for="page-title">
                  <Translate contentKey="hahApp.productPage.title">Title</Translate>
                </Label>
                <AvField id="page-title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label for="page-page">
                  <Translate contentKey="hahApp.productPage.page">Page</Translate>
                </Label>
                <AvInput
                  id="page-page"
                  type="select"
                  className="form-control"
                  name="page.id"
                  value={isNew ? products[0] && products[0].id : pageEntity.page.id}
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
              <AvGroup>
                <Label for="page-order">
                  <Translate contentKey="hahApp.productPage.order">Order</Translate>
                </Label>
                <AvInput
                  id="page-order"
                  type="select"
                  className="form-control"
                  name="order.id"
                  value={isNew ? fields[0] && fields[0].id : pageEntity.order.id}
                  required
                >
                  {fields
                    ? fields.map(otherEntity => (
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
              <Button tag={Link} id="cancel-save" to="/page" replace color="info">
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
  fields: storeState.field.entities,
  pageEntity: storeState.page.entity,
  loading: storeState.page.loading,
  updating: storeState.page.updating,
  updateSuccess: storeState.page.updateSuccess
});

const mapDispatchToProps = {
  getProducts,
  getFields,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PageUpdate);

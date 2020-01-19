import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './product.reducer';
import { IProduct } from 'app/shared/model/product/product.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductDetail = (props: IProductDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { productEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="hahApp.productProduct.detail.title">Product</Translate> [<b>{productEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="additionalReceipts">
              <Translate contentKey="hahApp.productProduct.additionalReceipts">Additional Receipts</Translate>
            </span>
          </dt>
          <dd>{productEntity.additionalReceipts}</dd>
          <dt>
            <span id="client">
              <Translate contentKey="hahApp.productProduct.client">Client</Translate>
            </span>
          </dt>
          <dd>{productEntity.client}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hahApp.productProduct.name">Name</Translate>
            </span>
          </dt>
          <dd>{productEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="hahApp.productProduct.description">Description</Translate>
            </span>
          </dt>
          <dd>{productEntity.description}</dd>
          <dt>
            <span id="paymentType">
              <Translate contentKey="hahApp.productProduct.paymentType">Payment Type</Translate>
            </span>
          </dt>
          <dd>{productEntity.paymentType}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="hahApp.productProduct.type">Type</Translate>
            </span>
          </dt>
          <dd>{productEntity.type}</dd>
          <dt>
            <span id="vatCode">
              <Translate contentKey="hahApp.productProduct.vatCode">Vat Code</Translate>
            </span>
          </dt>
          <dd>{productEntity.vatCode}</dd>
          <dt>
            <Translate contentKey="hahApp.productProduct.category">Category</Translate>
          </dt>
          <dd>{productEntity.category ? productEntity.category.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/product" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ product }: IRootState) => ({
  productEntity: product.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductDetail);

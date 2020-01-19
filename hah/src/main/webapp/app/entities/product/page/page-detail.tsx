import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './page.reducer';
import { IPage } from 'app/shared/model/product/page.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PageDetail = (props: IPageDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { pageEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="hahApp.productPage.detail.title">Page</Translate> [<b>{pageEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="number">
              <Translate contentKey="hahApp.productPage.number">Number</Translate>
            </span>
          </dt>
          <dd>{pageEntity.number}</dd>
          <dt>
            <span id="predicates">
              <Translate contentKey="hahApp.productPage.predicates">Predicates</Translate>
            </span>
          </dt>
          <dd>{pageEntity.predicates}</dd>
          <dt>
            <span id="protectionLevel">
              <Translate contentKey="hahApp.productPage.protectionLevel">Protection Level</Translate>
            </span>
          </dt>
          <dd>{pageEntity.protectionLevel}</dd>
          <dt>
            <span id="quote">
              <Translate contentKey="hahApp.productPage.quote">Quote</Translate>
            </span>
          </dt>
          <dd>{pageEntity.quote}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="hahApp.productPage.title">Title</Translate>
            </span>
          </dt>
          <dd>{pageEntity.title}</dd>
          <dt>
            <Translate contentKey="hahApp.productPage.page">Page</Translate>
          </dt>
          <dd>{pageEntity.page ? pageEntity.page.code : ''}</dd>
          <dt>
            <Translate contentKey="hahApp.productPage.order">Order</Translate>
          </dt>
          <dd>{pageEntity.order ? pageEntity.order.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/page" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/page/${pageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ page }: IRootState) => ({
  pageEntity: page.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PageDetail);

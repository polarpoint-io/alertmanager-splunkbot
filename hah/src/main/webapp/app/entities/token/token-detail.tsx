import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './token.reducer';
import { IToken } from 'app/shared/model/token.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITokenDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TokenDetail = (props: ITokenDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { tokenEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="hahApp.token.detail.title">Token</Translate> [<b>{tokenEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="clientAccountName">
              <Translate contentKey="hahApp.token.clientAccountName">Client Account Name</Translate>
            </span>
          </dt>
          <dd>{tokenEntity.clientAccountName}</dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="hahApp.token.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{tokenEntity.clientId}</dd>
          <dt>
            <span id="iin">
              <Translate contentKey="hahApp.token.iin">Iin</Translate>
            </span>
          </dt>
          <dd>{tokenEntity.iin}</dd>
          <dt>
            <span id="itemId">
              <Translate contentKey="hahApp.token.itemId">Item Id</Translate>
            </span>
          </dt>
          <dd>{tokenEntity.itemId}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hahApp.token.name">Name</Translate>
            </span>
          </dt>
          <dd>{tokenEntity.name}</dd>
          <dt>
            <span id="svcStart">
              <Translate contentKey="hahApp.token.svcStart">Svc Start</Translate>
            </span>
          </dt>
          <dd>{tokenEntity.svcStart}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="hahApp.token.type">Type</Translate>
            </span>
          </dt>
          <dd>{tokenEntity.type}</dd>
          <dt>
            <Translate contentKey="hahApp.token.token">Token</Translate>
          </dt>
          <dd>{tokenEntity.token ? tokenEntity.token.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/token" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/token/${tokenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ token }: IRootState) => ({
  tokenEntity: token.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TokenDetail);

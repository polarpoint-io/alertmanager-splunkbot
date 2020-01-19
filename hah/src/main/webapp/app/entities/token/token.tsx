import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './token.reducer';
import { IToken } from 'app/shared/model/token.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITokenProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Token = (props: ITokenProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { tokenList, match } = props;
  return (
    <div>
      <h2 id="token-heading">
        <Translate contentKey="hahApp.token.home.title">Tokens</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="hahApp.token.home.createLabel">Create new Token</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {tokenList && tokenList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.token.clientAccountName">Client Account Name</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.token.clientId">Client Id</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.token.iin">Iin</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.token.itemId">Item Id</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.token.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.token.svcStart">Svc Start</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.token.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.token.token">Token</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tokenList.map((token, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${token.id}`} color="link" size="sm">
                      {token.id}
                    </Button>
                  </td>
                  <td>{token.clientAccountName}</td>
                  <td>{token.clientId}</td>
                  <td>{token.iin}</td>
                  <td>{token.itemId}</td>
                  <td>{token.name}</td>
                  <td>{token.svcStart}</td>
                  <td>{token.type}</td>
                  <td>{token.token ? <Link to={`product/${token.token.id}`}>{token.token.code}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${token.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${token.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${token.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          <div className="alert alert-warning">
            <Translate contentKey="hahApp.token.home.notFound">No Tokens found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ token }: IRootState) => ({
  tokenList: token.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Token);

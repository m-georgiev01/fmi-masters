import { Navigate } from "react-router";
import { GuardProps } from "./GuardsProps";
import { ReactNode } from "react";
import React from "react";

export class NonAuthenticatedRoute extends React.Component<GuardProps> {
    render(): ReactNode {
        if (this.props.state.user) {
            return <Navigate to="/main" />
        }

        return this.props.children;
    }   
}
import { Navigate } from "react-router";
import { GuardProps } from "./GuardsProps";
import React from "react";

export class AuthenticatedRoute extends React.Component<GuardProps> {
    render(): React.ReactNode {
        if (!this.props.state.user) {
            return <Navigate to="/login" />
        }

        return this.props.children;
    }
}

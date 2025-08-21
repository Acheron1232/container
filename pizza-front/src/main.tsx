import { Fragment } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import OidcProvider from './auth/oidc'
import { CartProvider } from './cart/CartContext'

createRoot(document.getElementById('root')!).render(
  <Fragment>
    <OidcProvider>
      <CartProvider>
        <App />
      </CartProvider>
    </OidcProvider>
  </Fragment>,
)

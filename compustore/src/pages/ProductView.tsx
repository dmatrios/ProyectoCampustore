import './Product.css';
import type { Product } from '../type/Producto';
import { useState, useEffect } from 'react';

function ProductView() {
  const [listaproductos, setListaproductos] = useState<Product[]>([]);

  useEffect(() => {
    leerServicio();
  }, []);

  const leerServicio = () => {
  fetch('http://localhost:8085/springboot-jpa/producto/controller/producto')
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      setListaproductos(data);
    })
    .catch((error) => {
      console.error('Error fetching data:', error);
    });
};


  return (
    <div className="product-list">
      {listaproductos.map((producto) => (
        <div key={producto.idproducto} className="product-item">
          <h3>{producto.nombre}</h3>
          <p>Precio: ${producto.precio}</p>
          <p>Stock: {producto.stock}</p>
          <p>Categoría: {producto.categoria.nombre}</p>
               <p>
            Descripcion:{' '}
            {producto.categoria && producto.categoria.descripcion
              ? producto.categoria.descripcion
              : 'Sin descripción'}
          </p>
        </div>
      ))}
    </div>
  );
}

export default ProductView;

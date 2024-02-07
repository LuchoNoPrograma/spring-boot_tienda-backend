package com.tiendadbii.tiendadbii.model.repository;

import com.tiendadbii.tiendadbii.model.entity.Venta;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
  @Query("SELECT v FROM Venta v WHERE v.fechaVenta BETWEEN ?1 AND ?2 ORDER BY v.nroVenta DESC")
  List<Venta> findByFechaVentaBetween(LocalDateTime start, LocalDateTime end);

  @Query(nativeQuery = true, value = """
    WITH dias_del_mes AS
             (SELECT generate_series(
                             CAST(?1 AS DATE), --Fecha inicio
                             CAST(?2 AS DATE), --Fecha fin
                             INTERVAL '1 day' --Intervalo de un dia
                     ) AS dia)
    SELECT DATE(ddm.dia)                                                          AS fecha,
           COALESCE(SUM(v.total_venta), 0)                                        AS venta_total_bruto,
           COALESCE(SUM(v.total_desc_venta), 0)                                   AS descuento_total,
           COALESCE(SUM(v.total_venta), 0) - COALESCE(SUM(v.total_desc_venta), 0) AS venta_total_neto
    FROM dias_del_mes ddm
             LEFT JOIN venta v ON DATE_TRUNC('day', v.fecha_venta) = DATE(ddm.dia)
    GROUP BY fecha
    ORDER BY fecha;
    """)
  List<Tuple> getVentaResumenBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
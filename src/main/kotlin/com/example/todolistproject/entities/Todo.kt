package com.example.todolistproject.entities

import com.example.todolistproject.enum.Status
import jakarta.persistence.*

@Entity
@Table(name = "todo")
class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "status", nullable = false)
    var status: Status? = null
)
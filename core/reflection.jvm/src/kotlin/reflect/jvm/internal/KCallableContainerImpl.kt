/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kotlin.reflect.jvm.internal

import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.resolve.scopes.JetScope
import kotlin.reflect.KotlinReflectionInternalError

abstract class KCallableContainerImpl {
    abstract val jClass: Class<*>

    abstract val scope: JetScope

    protected fun findPropertyDescriptor(name: String): () -> PropertyDescriptor = {
        val properties = scope.getProperties(Name.identifier(name))
        if (properties.size() != 1) {
            throw KotlinReflectionInternalError(
                    if (properties.isEmpty()) "Property $name not resolved in $this"
                    else "${properties.size()} properties with name $name resolved in $this"
            )
        }

        properties.single() as PropertyDescriptor
    }
}

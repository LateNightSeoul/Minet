package com.Minet.Minet.jwt;

import com.Minet.Minet.domain.common.Id;
import com.Minet.Minet.domain.member.Email;
import com.Minet.Minet.domain.member.Member;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;

public class JwtAuthentication {
    public final Id<Member, Long> id;

    public final String name;

    public final Email email;

    public JwtAuthentication(Long id, String name, Email email) {
        checkArgument(id != null, "id must be provided.");
        checkArgument(name != null, "name must be provided.");
        checkArgument(email != null, "email must be provided.");

        this.id = Id.of(Member.class, id);
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("email", email)
                .toString();
    }
}
